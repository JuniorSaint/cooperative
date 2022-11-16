package br.com.cooperative.models.Response;

import br.com.cooperative.models.entities.Notification;

import java.util.List;
import java.util.UUID;

public class UserNotificationResponse {
    private UUID id;
    private String userName;
    private String email;
    private List<Notification> notifications;
}
