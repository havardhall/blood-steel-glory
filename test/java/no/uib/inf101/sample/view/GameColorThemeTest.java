package no.uib.inf101.sample.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.Font;

import org.junit.jupiter.api.Test;

public class GameColorThemeTest {
  /**
  * Checks that every tile is the color that it is supposed to be
  * and that all colors are the right ones
  */
  @Test
  public void sanityDefaultColorThemeTest() {
    ColorTheme colors = new GameColorTheme();
    // Font and backgrounds
    // Main Font - test
    assertEquals(new Font("Serif", Font.BOLD, 24), colors.getUIFont());
    // Menu Font - test
    assertEquals(new Font("Serif", Font.BOLD, 40), colors.getMenuFont());
    // Font Color - test
    assertEquals(new Color(0xC0C0C0), colors.getFontColor());
    // Frame Color - test
    assertEquals(new Color(0x06768d), colors.getBackgroundColor());
    // Void Color - test
    assertEquals(new Color(0,0,0, 0), colors.getVoidColor());
    // Primary Color - test
    assertEquals(new Color(0x003747), colors.getPrimaryColor());
    // Secondary Color - test
    assertEquals(new Color(0x06768d), colors.getSecondaryColor());
    // Tertiary Color - test
    assertEquals(new Color(0xC0C0C0), colors.getTertiaryColor());
  }
}