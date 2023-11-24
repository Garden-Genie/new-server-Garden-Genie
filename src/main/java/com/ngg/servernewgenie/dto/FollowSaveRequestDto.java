package com.ngg.servernewgenie.dto;

import com.ngg.servernewgenie.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FollowSaveRequestDto {
    private Long fromUserId;
    private Long toUserId;
}
