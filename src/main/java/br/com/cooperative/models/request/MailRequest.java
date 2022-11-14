package br.com.cooperative.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class MailRequest {
    private String name;
    private String to;
    private String from;
    private String subject;
}
