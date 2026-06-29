package com.example.jreg0.Util;

import com.example.jreg0.utils.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


public class StopStationListTest {
    private StopStationList newList(String... stations) {
        return new StopStationList(new LinkedList<>(List.of(stations)));
    }

    private StopStationList list;

    @BeforeEach
    void setUp() {
        list = newList("TKY01", "UEN02", "OMY03", "SND11");
    }

    @Nested
    class getList {
        @Test
        void 駅ID渡された順に返す() {
            assertThat(list.getList())
                    .containsExactly("TKY01", "UEN02", "OMY03", "SND11");
        }

        @Test
        void 変更不可のリストを返す() {
            assertThatThrownBy(() -> list.getList().add("KSR999"))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    class getFirst_getLast {
        @Test
        void 始発駅を返す() {
            assertEquals(Optional.of("TKY01"), list.getFirst());
        }

        @Test
        void 終点駅を返す() {
            assertEquals(Optional.of("SND11"), list.getLast());
        }

        @Test
        void 駅がない場合は空を返す() {
            StopStationList list = new StopStationList(new LinkedList<>());

            assertAll(
                    () -> assertThat(list.getFirst()).isEmpty(),
                    () -> assertThat(list.getLast()).isEmpty()
            );
        }

        @Nested
        class getNext {
            @Test
            void 指定した駅の次の駅IDを返す() {
                assertEquals(Optional.of("OMY03"), list.getNext("UEN02"));
            }

            @Test
            void 終点駅の場合は空を返す() {
                assertEquals(Optional.empty(), list.getNext("SND11"));
            }

            @Test
            void 存在しない駅に対しては空を返す() {
                assertEquals(Optional.empty(), list.getNext("KSR999"));
            }
        }

        @Nested
        class getPrev {
            @Test
            void 指定した駅の前の駅IDを返す() {
                assertEquals(Optional.of("TKY01"), list.getPrev("UEN02"));
            }

            @Test
            void 始発の場合は空を返す() {
                assertEquals(Optional.empty(), list.getPrev("TKY01"));
            }

            @Test
            void 存在しない駅に対しては空を返す() {
                assertEquals(Optional.empty(), list.getPrev("KSR999"));
            }
        }

        @Nested
        class getRange {
            @Test
            void 両端を含む駅を順に返す() {
                assertThat(list.getRange("TKY01", "OMY03"))
                        .contains(List.of("TKY01", "UEN02", "OMY03"));
            }

            @Test
            void 両端が同じ駅なら1駅返す() {
                assertEquals(Optional.of(List.of("UEN02")), list.getRange("UEN02", "UEN02"));
            }

            @Test
            void 両端の順番が逆なら空を返す() {
                assertEquals(Optional.empty(), list.getRange("OMY03", "UEN02"));
            }
        }
    }
}
