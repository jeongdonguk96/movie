package io.spring.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "TB_PEOPLEMOVIE")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class PeopleMovie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "peoplemovie_id")
    private Long peopleMovieId;

    @ManyToOne
    @JoinColumn(name = "actor_people_code")
    private Actor actor;

    @ManyToOne
    @JoinColumn(name = "director_people_code")
    private Director director;

    @ManyToOne
    @JoinColumn(name = "movie_code")
    private Movie movie;
}
