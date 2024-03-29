package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  @BeforeEach
  public void init(){
    primaryTorpedoStore = mock(TorpedoStore.class);
    secondaryTorpedoStore = mock(TorpedoStore.class);

    this.ship = new GT4500(primaryTorpedoStore, secondaryTorpedoStore);

    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert

    verify(primaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert

    verify(primaryTorpedoStore, times(1)).fire(1);
    verify(secondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_SecondTime() {
    // Arrange

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert

    verify(primaryTorpedoStore, times(1)).fire(1);
    verify(secondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_ThirdTime() {
    // Arrange

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryTorpedoStore, times(2)).fire(1);
    verify(secondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_FirstTime_PrimaryEmpty() {
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(secondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_SecondTime_SecondaryEmpty() {
    // Arrange
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert

    verify(primaryTorpedoStore, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_FirstTime_BothEmpty() {
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);

    verify(primaryTorpedoStore, times(0)).fire(1);
    verify(secondaryTorpedoStore, times(0)).fire(1);
  }

  
  @Test
  public void fireTorpedo_All_PrimaryEmpty() {
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    verify(primaryTorpedoStore, times(0)).fire(1);
    verify(secondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_SecondaryEmpty() {
    // Arrange
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    verify(primaryTorpedoStore, times(1)).fire(1);
    verify(secondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_SecondTime_PrimaryHasOneTorpedo_SecondaryEmpty() {
    // Arrange
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert

    verify(primaryTorpedoStore, times(1)).fire(1);
    verify(secondaryTorpedoStore, times(0)).fire(1);
  }
}
