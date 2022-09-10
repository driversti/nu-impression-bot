package com.github.driversti.nuimpressionbot.common;

import com.github.driversti.nuimpressionbot.building.Cage;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import lombok.experimental.UtilityClass;

// TODO: probably this should be configured once, before the app goes into production (a config file?)
@UtilityClass
public class BuildingModel {

  private static final ImmutableMap<Cage, ImmutableMap<Floor, ImmutableList<Integer>>> BUILDING_MODEL = ImmutableMap
      .of(
          Cage.A, ImmutableMap.of(
              Floor.GROUND, ImmutableList.of(1, 2, 3, 4, 5, 6),
              Floor.FIRST, ImmutableList.of(7, 8, 9, 10, 11, 12),
              Floor.SECOND, ImmutableList.of(13, 14, 15, 16, 17, 18),
              Floor.THIRD, ImmutableList.of(19, 20, 21, 22, 23, 24),
              Floor.FOURTH, ImmutableList.of(25, 26, 27, 28, 29, 30),
              Floor.FIFTH, ImmutableList.of(31, 32, 33, 34, 35, 36),
              Floor.SIXTH, ImmutableList.of(37, 38, 39, 40, 41, 42),
              Floor.SEVENTH, ImmutableList.of(43, 44, 45, 46, 47)
          ),
          Cage.B, ImmutableMap.of(
              Floor.GROUND, ImmutableList.of(48, 49, 50, 51),
              Floor.FIRST, ImmutableList.of(52, 53, 54, 55),
              Floor.SECOND, ImmutableList.of(56, 57, 58, 59),
              Floor.THIRD, ImmutableList.of(60, 61, 62, 63),
              Floor.FOURTH, ImmutableList.of(64, 65, 66, 67),
              Floor.FIFTH, ImmutableList.of(68, 69, 70, 71),
              Floor.SIXTH, ImmutableList.of(72, 73, 74, 75),
              Floor.SEVENTH, ImmutableList.of(76, 77, 78, 79)
          ),
          Cage.C, ImmutableMap.of(
              Floor.GROUND, ImmutableList.of(80, 81),
              Floor.FIRST, ImmutableList.of(82, 83, 84, 85, 86, 87),
              Floor.SECOND, ImmutableList.of(88, 89, 90, 91, 92, 93),
              Floor.THIRD, ImmutableList.of(94, 95, 96, 97, 98, 99),
              Floor.FOURTH, ImmutableList.of(100, 101, 102, 103, 104, 105),
              Floor.FIFTH, ImmutableList.of(106, 107, 108, 109, 110, 111),
              Floor.SIXTH, ImmutableList.of(112, 113, 114, 115, 116, 117),
              Floor.SEVENTH, ImmutableList.of(118, 119, 120, 121, 122, 123)
          ),
          Cage.D, ImmutableMap.of(
              Floor.GROUND, ImmutableList.of(124),
              Floor.FIRST, ImmutableList.of(125, 126, 127, 128, 129, 130),
              Floor.SECOND, ImmutableList.of(131, 132, 133, 134, 135, 136),
              Floor.THIRD, ImmutableList.of(137, 139, 140, 141, 142),
              Floor.FOURTH, ImmutableList.of(143, 144, 145, 146, 147, 148),
              Floor.FIFTH, ImmutableList.of(149, 150, 151, 152, 153, 154),
              Floor.SIXTH, ImmutableList.of(155, 156, 157, 158, 159, 160),
              Floor.SEVENTH, ImmutableList.of(161, 162, 163, 164, 165, 166)
          ),
          Cage.E, ImmutableMap.of(
              Floor.GROUND, ImmutableList.of(),
              Floor.FIRST, ImmutableList.of(167, 168, 169, 170, 171, 172),
              Floor.SECOND, ImmutableList.of(173, 174, 175, 176, 177, 178),
              Floor.THIRD, ImmutableList.of(179, 180, 181, 182, 183, 184),
              Floor.FOURTH, ImmutableList.of(185, 186, 187, 188, 189, 190),
              Floor.FIFTH, ImmutableList.of(191, 192, 193, 194, 195, 196),
              Floor.SIXTH, ImmutableList.of(197, 198, 199, 200, 201, 202),
              Floor.SEVENTH, ImmutableList.of(203, 204, 205, 206, 207, 208)
          ),
          Cage.F, ImmutableMap.of(
              Floor.GROUND, ImmutableList.of(),
              Floor.FIRST, ImmutableList.of(209, 210, 211, 212),
              Floor.SECOND, ImmutableList.of(213, 214, 215, 216),
              Floor.THIRD, ImmutableList.of(217, 218, 219, 220),
              Floor.FOURTH, ImmutableList.of(221, 222, 223, 224),
              Floor.FIFTH, ImmutableList.of(225, 226, 227, 228),
              Floor.SIXTH, ImmutableList.of(229, 230, 231, 232),
              Floor.SEVENTH, ImmutableList.of(233, 234, 235)
          ),
          Cage.G, ImmutableMap.of(
              Floor.GROUND, ImmutableList.of(),
              Floor.FIRST, ImmutableList.of(236, 237, 238, 239),
              Floor.SECOND, ImmutableList.of(240, 241, 242, 243),
              Floor.THIRD, ImmutableList.of(244, 245, 246, 247),
              Floor.FOURTH, ImmutableList.of(248, 249, 250, 251),
              Floor.FIFTH, ImmutableList.of(252, 253, 254, 255),
              Floor.SIXTH, ImmutableList.of(256, 257, 258, 259),
              Floor.SEVENTH, ImmutableList.of(260, 261, 262)
          )
      );

  public static Collection<Integer> apartments(Cage cage, Floor floor) {
    ImmutableMap<Floor, ImmutableList<Integer>> floorApartments = BUILDING_MODEL.get(cage);
    return floorApartments.get(floor);
  }
}
