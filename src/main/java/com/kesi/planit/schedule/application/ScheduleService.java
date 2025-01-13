package com.kesi.planit.schedule.application;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.application.repository.ScheduleAndCalendarRepo;
import com.kesi.planit.schedule.application.repository.ScheduleRepo;
import com.kesi.planit.schedule.application.repository.ScheduleSecurityRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.infrastructure.ScheduleAndCalendarJpaEntity;
import com.kesi.planit.schedule.infrastructure.ScheduleJpaEntity;
import com.kesi.planit.schedule.infrastructure.ScheduleSecurityEntity;
import com.kesi.planit.schedule.presentation.dto.PersonalScheduleDto;
import com.kesi.planit.schedule.presentation.dto.ScheduleDto;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private final ScheduleSecurityRepo scheduleSecurityRepo;
    private final ScheduleAndCalendarRepo scheduleAndCalendarRepo;


    //개인일정인 추가된 경우 속한 그룹중 자신의 일정을 공유하기로한 그룹 캘린더 아이디를 추가
    public void addPersonalSchedule(User user, PersonalScheduleDto personalScheduleDto, List<Group> excludeGroup) {
        //스케줄 도메인 객체 생성 및 저장
        Schedule  schedule = this.save(personalScheduleDto.toModel(user));

        //security 정보 생성 및 저장
        ScheduleSecurityEntity scheduleSecurityEntity =
                scheduleSecurityRepo.save(
                    ScheduleSecurityEntity.builder()
                    .securityLevel(personalScheduleDto.securityLevel).scheduleId(schedule.getId())
                    .uid(user.getUid()).build()
                );

        //자신이 속한 그룹들을 가져온다.
        List<Group> groups = groupService.getByUid(user.getUid());

        //특정 그룹을 제외(선택사항)
        if(excludeGroup != null) groups.removeAll(excludeGroup);

        //속한 그룹들 중 연결 관계에 따라서 스케줄에 캘린더를 연결한다.
        groups.forEach(it -> {
            //연결 정보 생성 및 저장
            scheduleAndCalendarRepo.save(ScheduleAndCalendarJpaEntity.builder()
                    .calendarId(it.getGroupCalendar().getId())
                    .scheduleSecurityId(scheduleSecurityEntity.getId())
                    .build());
        });
    }


    //그룹 캘린더에 스케줄 추가 시도 -> 개인들의 동의 필요
    private void addGroupSchedule(Long calendarId, Schedule schedule) {
        //캘린더 id로 그룹을 찾는다.
        Group group = groupService.getByCalendarId(calendarId);

        //Todo. 개인들에게 일정을 동의를 구하는 메시지를 전송하는 메서드 필요
        //각 사용자의 속한 그룹 최신화(현재 속한 그룹을 제외)
        group.getUsers().values().forEach(it -> {
            //addPersonalSchedule(it.getUser(), calendarId, schedule, List.of(group));
        });
    }


    public Schedule getById(Long id) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleRepo.findById(id);
        User maker = userService.getUserById(scheduleJpaEntity.getMakerUid());
        Calendar calendar = calendarService.getById(scheduleJpaEntity.getSourceCalendarId());


        //Schedule 객체를 도메인 객체로 매핑하기 위해 Calendars 가 필요함.
        return scheduleJpaEntity.toModel(maker, calendar);
    }



    public Schedule save(Schedule schedule) {
        return  scheduleRepo.save(ScheduleJpaEntity.from(schedule)).toModel(
                schedule.getMaker(), schedule.getSourceCalendar());
    }


    //캘린더 아이디로 스케줄을 조회
    //너무 많은 양이 조회될 수 있음으로 나중에 성능개선에 대해서 생각해 볼 필요가 있음
    public List<ScheduleDto> getByCalendarId(Long calendarId) {
        List<ScheduleAndCalendarJpaEntity> scheduleAndCalendarJpaEntities
                = scheduleAndCalendarRepo.findByCalendarId(calendarId);


        //Todo. securityLevel에 의해서
        return null; //scheduleAndCalendarJpaEntities.stream().map(it -> getById(it.getScheduleId())).toList();
    }
}
