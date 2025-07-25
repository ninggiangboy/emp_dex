package dev.ngb.empdex.shared.event;

import dev.ngb.empdex.shared.core.event.Event;

public record SendSignInCodeEmailEvent(String email, String code, long expirationInMinutes) implements Event {
}
