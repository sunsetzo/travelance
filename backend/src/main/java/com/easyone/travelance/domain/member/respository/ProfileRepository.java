package com.easyone.travelance.domain.member.respository;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.entity.Profile;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByMember(Member member);

    Profile findByMemberAndTravelRoom(Member member, TravelRoom travelRoom);
}
