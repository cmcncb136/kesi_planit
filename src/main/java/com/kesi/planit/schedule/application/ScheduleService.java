package com.kesi.planit.schedule.application;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.core.CommonResult;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.application.repository.GroupAndUserRepo;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.group.infrastructure.GroupAndUserJpaEntity;
import com.kesi.planit.schedule.application.repository.ScheduleAndCalendarRepo;
import com.kesi.planit.schedule.application.repository.ScheduleRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.infrastructure.ScheduleAndCalendarJpaEntity;
import com.kesi.planit.schedule.infrastructure.ScheduleJpaEntity;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;
    //순환 참조 주의
    //Calendar가 List<Schedule>를 가지 않게하는 이유는 Calendar
    //조회시 너무 많은 Schedule select 되지 않기 위함이다.
    private final CalendarService calendarService;
    private final UserService userService;
    private final GroupService groupService;

    private final ScheduleAndCalendarRepo scheduleAndCalendarRepo;
    private final GroupAndUserRepo groupAndUserRepo;



    //Todo. 스케줄을 추가할 때 동작할 함수 구현
    public CommonResult addSchedule(String uid, Long calendarId, Schedule schedule) {
        User user = userService.getUserById(uid);

        //GroupCalendar 수정 --> 각 유저에 채팅방에 있는 Calendar 수정 --> 각 유저 채팅방에 있는 GroupCalendar 수정 --> 각 유저에 채팅방에 있는 Calendar 수정 --> 또 그 방에 잇는 개인 캘린더 수정 --> Group 캘린더 수정
        //loop에 빠질 수 있음
        //업데이트 양이 상당함....
        //개인이 캘리더를 수정했을 때 모든 방에 캘린더를 업데이트 해야됨... 뭐지? 이렇게 많나?
        //나중에 인당 만들 수 있는 채팅방에 수를 조절해야 되나...
        //어 아니넹... 그룹 캘린더에 일정이 추가 되었을 때 개인의 일정 변경 및 개인이 속해 있는 그룹 캘린더 일정만 변경하면 되넹...

        if(user.getMyCalendar().getId() == calendarId){ //개인 캘린더에 일정을 추가하는 경우
            addPersonalSchedule(user, calendarId, schedule);
        }else{
            addGroupSchedule(calendarId, schedule);
        }

        return null; //뭘 반환할지 고민
    }

    public void addPersonalSchedule(User user, Long calendarId, Schedule schedule) {
        addPersonalSchedule(user, calendarId, schedule, null);
    }

    //개인일정인 추가된 경우 속한 그룹중 자신의 일정을 공유하기로한 그룹 캘린더 아이디를 추가
    private void addPersonalSchedule(User user, Long calendarId, Schedule schedule, List<Group> excludeGroup) {
        //자신 캘린더에 일정 추가
        schedule.getCalendars().add(
                Schedule.ScheduleReferCalendar.builder()
                        .calendar(user.getMyCalendar())
                        .access(true)
                        .build()
        );

        //자신이 속한 그룹을 가져온다.
        List<Group> groups = groupService.getByUid(user.getUid());

        //특정 그룹을 제외(선택사항)
        if(excludeGroup != null) groups.removeAll(excludeGroup);

        //속한 그룹들 중 연결 관계에 따라서 일정을 추가한다.
        groups.forEach(it -> {
            Group.GroupInUser groupInUser = it.getUsers().get(user.getUid());
            if(groupInUser.isOpen()) //유저가 자신의 캘린더에 접근을 허용한 경우에만

                schedule.getCalendars().add(
                        Schedule.ScheduleReferCalendar.builder()
                                .access(groupInUser.isAccess())//디테일한 정보를 공유할지 여부
                                .calendar(it.getGroupCalendar())
                                .build()
                );
        });

        save(schedule);
    }

    //그룹 캘린더를 최신화 --> 각 사용자 일정 최신화 --> 각 사용자들의 그룹 캘린더 최신화
    private void addGroupSchedule(Long calendarId, Schedule schedule) {
        //캘린더 id로 그룹을 찾는다.
        Group group = groupService.getByCalendarId(calendarId);

        //그룹 캘린더 일정 추가
        schedule.getCalendars().add(
                Schedule.ScheduleReferCalendar.builder()
                        .calendar(group.getGroupCalendar())
                        .access(true)
                        .build()
        );

        //각 사용자의 속한 그룹 최신화(현재 속한 그룹을 제외)
        group.getUsers().values().forEach(it -> {
            addPersonalSchedule(it.getUser(), calendarId, schedule, List.of(group));
        });
    }


    public Schedule getById(Long id) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleRepo.findById(id);

        User maker = userService.getUserById(scheduleJpaEntity.getMakerUid());

        List<ScheduleAndCalendarJpaEntity> scheduleAndCalendars = scheduleAndCalendarRepo.findByScheduleId(id);

        //Schedule 객체를 도메인 객체로 매핑하기 위해 Calendars 가 필요함.
        return scheduleRepo.findById(id).toModel(maker,
                scheduleAndCalendars.stream().map(it -> it.toScheduleReferCalendar(
                        calendarService.getById(it.getCalendarId())
                )).toList());
    }


    public Schedule save(Schedule schedule) {
        return  scheduleRepo.save(ScheduleJpaEntity.from(schedule)).toModel(
                schedule.getMaker(),
                schedule.getCalendars()
        );
    }


    public List<Schedule> getByCalendarId(Long calendarId) {
        List<ScheduleAndCalendarJpaEntity> scheduleAndCalendarJpaEntities
                = scheduleAndCalendarRepo.findByCalendarId(calendarId);

        return scheduleAndCalendarJpaEntities.stream().map(it -> getById(it.getScheduleId())).toList();
    }


}
