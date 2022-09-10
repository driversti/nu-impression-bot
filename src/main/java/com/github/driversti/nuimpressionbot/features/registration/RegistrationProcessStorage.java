package com.github.driversti.nuimpressionbot.features.registration;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.github.driversti.nuimpressionbot.building.Cage;
import com.github.driversti.nuimpressionbot.common.Floor;
import com.github.driversti.nuimpressionbot.common.ParkingSpaceLocation;
import com.github.driversti.nuimpressionbot.features.registration.apartment.number.RegisterApartmentNumberReceivedEvent;
import com.github.driversti.nuimpressionbot.features.registration.apartment.cage.RegisterCageReceivedEvent;
import com.github.driversti.nuimpressionbot.features.registration.apartment.floor.RegisterFloorReceivedEvent;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegistrationProcessStorage {

  private final Map<Long, RegistrationProcess> ONGOING_PROCESSES = new HashMap<>();

  // TODO: we assume an optimistic scenario. Consider improvements
  public Cage cageFor(Long chatId) {
    return ONGOING_PROCESSES.get(chatId).cage;
  }

  public Floor floorFor(Long chatId) {
    return ONGOING_PROCESSES.get(chatId).floor;
  }

  public Integer apartmentFor(Long chatId) {
    return ONGOING_PROCESSES.get(chatId).apartment;
  }

  public boolean expectsCage(Long chatId) {
    return ONGOING_PROCESSES.containsKey(chatId) &&
        ONGOING_PROCESSES.get(chatId).expectsCage();
  }

  public boolean expectsFloor(Long chatId) {
    return ONGOING_PROCESSES.containsKey(chatId) &&
        ONGOING_PROCESSES.get(chatId).expectsFloor();
  }

  public boolean expectsApartment(Long chatId) {
    return ONGOING_PROCESSES.containsKey(chatId) &&
        ONGOING_PROCESSES.get(chatId).expectsApartment();
  }

  @EventListener
  public void newProcess(RegistrationConsentReceivedEvent event) {
    Long chatId = event.chatId();
    ONGOING_PROCESSES.put(chatId, new RegistrationProcess(chatId));
    log.info("New property registration process created");
  }

  @EventListener
  public void addCage(RegisterCageReceivedEvent event) {
    Long chatId = event.chatId();
    checkProcessExists(chatId);
    Cage cage = event.cage();
    ONGOING_PROCESSES.get(chatId).cage = cage;
    log.info("Property registration process: added cage {} for chatId {}", cage, chatId);
  }

  @EventListener
  public void addFloor(RegisterFloorReceivedEvent event) {
    Long chatId = event.chatId();
    checkProcessExists(chatId);
    Floor floor = event.floor();
    ONGOING_PROCESSES.get(chatId).floor = floor;
    log.info("Property registration process: added floor {} for chatId {}", floor, chatId);
  }

  @EventListener
  public void addApartment(RegisterApartmentNumberReceivedEvent event) {
    Long chatId = event.chatId();
    checkProcessExists(chatId);
    Integer apartment = event.apartment();
    ONGOING_PROCESSES.get(chatId).apartment = apartment;
    log.info("Property registration process: added apartment {} for chatId {}", apartment, chatId);
  }

  private void checkProcessExists(Long chatId) {
    if (!ONGOING_PROCESSES.containsKey(chatId)) {
      // TODO: should the Developer get the message?
      log.warn("This should not happen!");
      throw new RuntimeException("This was not expected on that step.");
    }
  }

  private final static class RegistrationProcess {

    private final Long chatId;
    private Cage cage;
    private Floor floor;
    private Integer apartment;
    private ParkingSpace parkingSpace;

    public RegistrationProcess(Long chatId) {
      this.chatId = chatId;
    }

    boolean expectsCage() {
      return nonNull(chatId) && isNull(cage);
    }
    boolean expectsFloor() {
      return nonNull(chatId) && nonNull(cage) && isNull(floor);
    }
    boolean expectsApartment() {
      return nonNull(chatId) && nonNull(cage) && nonNull(floor) && isNull(apartment);
    }
  }

  private static class ParkingSpace {

    private ParkingSpaceLocation location;
    private Integer number;
  }
}
