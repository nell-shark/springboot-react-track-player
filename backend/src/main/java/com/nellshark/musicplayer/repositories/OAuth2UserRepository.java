package com.nellshark.musicplayer.repositories;

import com.nellshark.musicplayer.models.AppOAuth2User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2UserRepository extends JpaRepository<AppOAuth2User, Integer> {
}
