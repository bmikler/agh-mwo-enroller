package com.company.enroller.meeting;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import com.company.enroller.participant.Participant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meeting")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private String name;

	@Column
	private String description;

	//@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "meeting_participant", joinColumns = { @JoinColumn(name = "meeting_id") }, inverseJoinColumns = {
			@JoinColumn(name = "participant_login") })
	Set<Participant> participants;

	public Meeting(String name, String description, Set<Participant> participants) {
		this.name = name;
		this.description = description;
		this.participants = participants;
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
		return id == meeting.id && name.equals(meeting.name) && description.equals(meeting.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description);
	}
}