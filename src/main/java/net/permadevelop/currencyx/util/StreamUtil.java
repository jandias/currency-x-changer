package net.permadevelop.currencyx.util;

import java.util.Optional;
import java.util.stream.Stream;


public class StreamUtil {
    /**
     * Extract optional T value into a Stream of one T element. If no value present in Optional an empty Stream is
     * returned.
     * This is a workaround for a Java 8 API small hole. Java 9 will have this issue solved: JDK-8050820
     *
     * @param optional to be extracted
     * @param <T>      Stream and Optional element type
     * @return
     */
    public static <T> Stream<T> extract(Optional<T> optional) {
        return optional.map(Stream::of).orElse(Stream.empty());
    }
}
