package com.wemakeitwork.allenvooreen.repository.event;

import com.wemakeitwork.allenvooreen.model.Member;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationSuccessEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String appUrl;
    private Locale locale;
    private Member member;

    public OnRegistrationSuccessEvent(Member member, String appUrl) {
        super(member);
        this.member = member;
        this.appUrl = appUrl;
    }
    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Member getMember() {
        return member;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
