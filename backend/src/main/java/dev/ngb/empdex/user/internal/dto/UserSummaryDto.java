package dev.ngb.empdex.user.internal.dto;

import java.sql.Timestamp;
import java.util.UUID;

public record UserSummaryDto(UUID id, String username, String email, String status, Timestamp lastLogin) {
}

