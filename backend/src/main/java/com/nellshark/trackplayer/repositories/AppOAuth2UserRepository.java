package com.nellshark.trackplayer.repositories;

import com.nellshark.trackplayer.models.AppOAuth2User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppOAuth2UserRepository extends JpaRepository<AppOAuth2User, Integer> {
}
