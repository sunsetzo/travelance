package com.easyone.travelance.domain.travel.entity;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.travel.dto.RoomInfoRequestDto;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name="room")
public class TravelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String travelName;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private RoomType isDone=RoomType.BEFORE;

    private Long budget;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "travelRoom", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<TravelRoomMember> travelRoomMembers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "travelRoom", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    public RoomType getIsDone() {
        return isDone;
    }

    @Builder
    public TravelRoom(String travelName, Long id, String location, LocalDateTime startDate, LocalDateTime endDate, RoomType isDone, Long budget) {
        this.travelName= travelName;
        this.id= id;
        this.budget= budget;
        this.endDate=endDate;
        this.startDate= startDate;
        this.location=location;
        this.isDone=isDone;
    }

    //여행방 수정
    public void update(RoomInfoRequestDto roomInfoRequestDto) {
        this.travelName= roomInfoRequestDto.getTravelName();
        this.location=roomInfoRequestDto.getLocation();
        this.startDate=roomInfoRequestDto.getStartDate();
        this.endDate=roomInfoRequestDto.getEndDate();
        this.budget= roomInfoRequestDto.getBudget();
    }

    public void setRoomType(RoomType roomType){
        this.isDone = roomType;
    }
}
