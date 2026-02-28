package backend.zbet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sport_event")
public class SportEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String team1;

    @Column(nullable = false)
    private String team2;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventResult result;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Column(nullable = false)
    private BigDecimal coefFirst;

    @Column(nullable = false)
    private BigDecimal coefSecond;

    @OneToMany(mappedBy = "event")
    private List<Bet> bets;

    public SportEvent() {
    }

    public SportEvent(Long id, String team1, String team2, EventResult result, EventStatus status, LocalDateTime startTime, LocalDateTime endTime, BigDecimal coefFirst, BigDecimal coefSecond, List<Bet> bets) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.result = result;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.coefFirst = coefFirst;
        this.coefSecond = coefSecond;
        this.bets = bets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public EventResult getResult() {
        return result;
    }

    public void setResult(EventResult result) {
        this.result = result;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getCoefFirst() {
        return coefFirst;
    }

    public void setCoefFirst(BigDecimal coefFirst) {
        this.coefFirst = coefFirst;
    }

    public BigDecimal getCoefSecond() {
        return coefSecond;
    }

    public void setCoefSecond(BigDecimal coefSecond) {
        this.coefSecond = coefSecond;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }
}
