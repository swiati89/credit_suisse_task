package model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EventData implements Serializable {
    private static final long serialVersionUID = 7384316023150662954L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @NonNull
    private Long id;
    private String eventId;
    private Long duration;
    private String host;
    private Boolean alert;
    private String type;
}
