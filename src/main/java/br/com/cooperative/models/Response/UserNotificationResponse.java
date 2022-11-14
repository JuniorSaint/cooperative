package br.com.cooperative.models.Response;

import br.com.cooperative.models.entities.Notification;

import java.util.List;

public class UserNotificationResponse {
    private Long id;
    private String userName;
    private String email;
    private List<Notification> notifications;
}
