package com.example.demo.Event.Service;

import com.example.demo.Event.Entity.Event;
import com.example.demo.Event.Repo.EventRepo;
import com.example.demo.Helpers.Helper;
import com.example.demo.domain.Family.Family;
import com.google.rpc.Help;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepo eventRepo;

    public Event saveEvent(Event event){
        return eventRepo.save(event);
    }

    public Event getById(int id){
        return eventRepo.getById(id);
    }

    @Transactional
    public void deleteEventsInFamily(int familyId){
        eventRepo.deleteEventsInFamily(familyId);
    }

    public ArrayList<Event> findAll(List<Integer> user,
                                   Family family,
                                   String title,
                                   boolean sortByDeadLine,
                                   Date from,
                                   Date to,
                                   int page,
                                   int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<String> users = (user != null) ? user.stream().map(user1 -> {
            return Integer.toString(user1);
        }).collect(Collectors.toList()) : List.of();

        ArrayList<Event> events = eventRepo.findAlLFilteredByUserAndStatusAndTitleSortedByCreatedAtOrDeadLine(
                family.getId(),
                users,
//                sb.toString(),
                title != null ? title.toLowerCase() : "",
                sortByDeadLine,
                (from != null) ? Helper.getInstance().formatDateWithTimeForQuery(from) : "",
                (to != null) ? Helper.getInstance().formatDateWithTimeForQuery(to) : "",
                pageable
        );

        return events;
    }

    public boolean checkIfDateContainEventsByFamilyId(String date, int familyId){
        int rs = eventRepo.findAnEventIdOnDateByFamilyId(date, familyId);
        return rs > 0;
    }

    public ArrayList<Event> findAllUpComingEventsIn30Mins(String timezone){
//        Date now = Helper.getInstance().getNowAsTimeZone(timezone);
        Date now = new Date();
        return eventRepo.findAllUpComingEventsIn30Mins(Helper.getInstance().formatDateWithTimeForQuery(now));
    }
}
