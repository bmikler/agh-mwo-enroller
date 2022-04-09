package com.company.enroller.meeting;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class MeetingRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String date;

    public MeetingRequest() {
    }

    public MeetingRequest(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingRequest that = (MeetingRequest) o;
        return title.equals(that.title) && description.equals(that.description) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, date);
    }

    @Override
    public String toString() {
        return "MeetingRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
