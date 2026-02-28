package backend.zbet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bet")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private SportEvent event;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BetChoice choice;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal coefAtMoment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BetStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Bet(Long id, AppUser user, SportEvent event, BetChoice choice, BigDecimal amount, BigDecimal coefAtMoment, BetStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.choice = choice;
        this.amount = amount;
        this.coefAtMoment = coefAtMoment;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public SportEvent getEvent() {
        return event;
    }

    public void setEvent(SportEvent event) {
        this.event = event;
    }

    public BetChoice getChoice() {
        return choice;
    }

    public void setChoice(BetChoice choice) {
        this.choice = choice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCoefAtMoment() {
        return coefAtMoment;
    }

    public void setCoefAtMoment(BigDecimal coefAtMoment) {
        this.coefAtMoment = coefAtMoment;
    }

    public BetStatus getStatus() {
        return status;
    }

    public void setStatus(BetStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Bet() {
    }
}