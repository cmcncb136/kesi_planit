package com.kesi.planit.schedule.application;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.application.repository.ScheduleAndCalendarRepo;
import com.kesi.planit.schedule.application.repository.ScheduleRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.infrastructure.ScheduleAndCalendarJpaEntity;
import com.kesi.planit.schedule.infrastructure.ScheduleJpaEntity;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<Schedule> addSchedule(String uid, Long calendarId, Schedule schedule) {
        User user = userService.getUserById(uid);

        //그룹 캘린더에 일정이 추가 되었을 때 개인의 일정 변경 및 개인이 속해 있는 그룹 캘린더 일정만 변경하면 됨
        save(schedule); //스케줄 저장

        //다른 캘린더에 추가(연결 관계를 저장)
        if(user.getMyCalendar().getId() == calendarId){ //개인 캘린더에 일정을 추가하는 경우
            addPersonalSchedule(user, calendarId, schedule);
        }else{
            addGroupSchedule(calendarId, schedule);
        }

        return ResponseEntity.ok(schedule); //뭘 반환할지 고민
    }

    //개인일정인 추가된 경우 속한 그룹중 자신의 일정을 공유하기로한 그룹 캘린더 아이디를 추가
    private void addPersonalSchedule(User user, Long calendarId, Schedule schedule, List<Group> excludeGroup) {
        //자신 캘린더에 일정 추가
        schedule.getCalendars().add(
                Schedule.ScheduleReferCalendar.builder()
                        .calendar(user.getMyCalendar()).build()
        );

        //자신이 속한 그룹들을 가져온다.
        List<Group> groups = groupService.getByUid(user.getUid());

        //특정 그룹을 제외(선택사항)
        if(excludeGroup != null) groups.removeAll(excludeGroup);

        //속한 그룹들 중 연결 관계에 따라서 스케줄에 캘린더를 연결한다.
        groups.forEach(it -> {
            Group.GroupInUser groupInUser = it.getUsers().get(user.getUid());

            if(groupInUser.isDetailedInfoShared()) { //유저가 자신의 일정을 공유한 그룹 캘린더에만 추가
                //연결 정보 생성 및 저장
                scheduleAndCalendarRepo.save(ScheduleAndCalendarJpaEntity.builder()
                        .calendarId(it.getGroupCalendar().getId()).scheduleId(schedule.getId())
                        .connectUserUid(user.getUid())
                        .build());
            }
        });
    }

    public void addPersonalSchedule(User user, Long calendarId, Schedule schedule) {
        addPersonalSchedule(user, calendarId, schedule, null);
    }

    //그룹 캘린더를 최신화 --> 각 사용자 일정 최신화 --> 각 사용자들의 그룹 캘린더 최신화
    private void addGroupSchedule(Long calendarId, Schedule schedule) {
        //캘린더 id로 그룹을 찾는다.
        Group group = groupService.getByCalendarId(calendarId);

        //캘린더&그룹 연결정보 정보 저장
        scheduleAndCalendarRepo.save(ScheduleAndCalendarJpaEntity.builder()
                .scheduleId(schedule.getId())
                .calendarId(calendarId).build());


        //각 사용자의 속한 그룹 최신화(현재 속한 그룹을 제외)
        group.getUsers().values().forEach(it -> {
            addPersonalSchedule(it.getUser(), calendarId, schedule, List.of(group));
        });
    }


    public Schedule getById(Long id) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleRepo.findById(id);
        User maker = userService.getUserById(scheduleJpaEntity.getMakerUid());
        Calendar sourceCalendar = calendarService.getById(scheduleJpaEntity.getSourceCalendarId());

        List<ScheduleAndCalendarJpaEntity> scheduleAndCalendars
                = scheduleAndCalendarRepo.findByScheduleId(id);

        //Schedule 객체를 도메인 객체로 매핑하기 위해 Calendars 가 필요함.
        return scheduleJpaEntity.toModel(maker, sourceCalendar,
                scheduleAndCalendars.stream().map(it -> it.toScheduleReferCalendar(
                        calendarService.getById(it.getCalendarId()), maker
                )).toList());
    }


    public Schedule createSchedule(Schedule schedule) {
        //스케줄 정보를 저장
        Schedule saveSchedule = save(schedule);

        //스케줄과 캘린더 관계를 연결
        saveSchedule.getCalendars().forEach(calender ->
                scheduleAndCalendarRepo.save(
                        ScheduleAndCalendarJpaEntity.builder()
                                .scheduleId(saveSchedule.getId())
                                .calendarId(calender.getCalendar().getId())
                                .build()
                )
        );

        return schedule;
    }

    public Schedule save(Schedule schedule) {
        return  scheduleRepo.save(ScheduleJpaEntity.from(schedule)).toModel(
                schedule.getMaker(), schedule.getSourceCalendar(),
                schedule.getCalendars()
        );
    }


    //캘린더 아이디로 스케줄을 조회
    //너무 많은 양이 조회될 수 있음으로 나중에 성능개선에 대해서 생각해 볼 필요가 있음
    public List<Schedule> getByCalendarId(Long calendarId) {
        List<ScheduleAndCalendarJpaEntity> scheduleAndCalendarJpaEntities
                = scheduleAndCalendarRepo.findByCalendarId(calendarId);

        return scheduleAndCalendarJpaEntities.stream().map(it -> getById(it.getScheduleId())).toList();
    }
}
