package com.company.enroller.meeting;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import com.company.enroller.participant.Participant;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "meeting")
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String title;

	@Column
	private String description;

	@Column
	private String date;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "meeting_participant", joinColumns = { @JoinColumn(name = "meeting_id") }, inverseJoinColumns = {
			@JoinColumn(name = "participant_login") })
	Set<Participant> participants = new HashSet<>();

	public Meeting() {

	}

	public Meeting(String title, String description, String date) {
		this.title = title;
		this.description = description;
		this.date = date;
	}

	public long getId() {
		return id;
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

	public void setId(Long id) {
		this.id = id;
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

	public void addParticipant(Participant participant) {
		this.participants.add(participant);
	}

	public void removeParticipant(Participant participant) {
		this.participants.remove(participant);
	}

	public Collection<Participant> getParticipants() {
		return participants;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Meeting meeting = (Meeting) o;
		return id == meeting.id && title.equals(meeting.title) && description.equals(meeting.description) && date.equals(meeting.date) && participants.equals(meeting.participants);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, description, date, participants);
	}

	@Override
	public String toString() {
		return "Meeting{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", date='" + date + '\'' +
				", participants=" + participants +
				'}';
	}
}