package com.company.enroller.participant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "participant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {

	@Id
	@NotBlank
	private String login;

	@Column
	@NotBlank
	private String password;

}
