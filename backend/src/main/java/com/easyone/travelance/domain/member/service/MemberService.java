package com.easyone.travelance.domain.member.service;


import com.easyone.travelance.domain.member.entity.Member;
//import com.easyone.travelance.domain.member.entity.MemberAuth;
//import com.easyone.travelance.domain.member.respository.MemberAuthRepository;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.global.error.ErrorCode;
import com.easyone.travelance.global.error.exception.AuthenticationException;
import com.easyone.travelance.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
//    private final MemberAuthRepository memberAuthRepository;

    public Member registerMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    public void updateMember(Member updatedMember) {
        Long memberId = updatedMember.getId();
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

//        // 업데이트할 필드가 있다면 각 필드를 새로운 값으로 업데이트
//        if (updatedMember.getFirebaseToken() != null) {
//            existingMember.setFirebaseToken(updatedMember.getFirebaseToken());
//        }

        // 회원 정보 업데이트
        memberRepository.save(existingMember);
    }

    // 중복 검증
    private void validateDuplicateMember(Member member) throws BusinessException {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember.isPresent()){
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Member findMemberByRefreshToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        LocalDateTime tokenExpirationTime = member.getTokenExpirationTime();
        if(tokenExpirationTime.isBefore(LocalDateTime.now())) {
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return member;
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_EXISTS));
    }



}
