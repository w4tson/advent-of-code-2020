/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.primitives.Ints.asList;

public class LibraryTest {
    @Test 
    public void firstExample() {
        Aoc aoc = new Aoc();
        List<Integer> adapters = asList(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4);
        aoc.solve(adapters);
    }

    @Test
    public void secondExample() {
        List<Integer> adapters = asList(28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23, 49, 45, 19, 38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3);
        new Aoc().solve(adapters);
    }

    @Test
    public void part1() throws IOException {
        Class clazz = LibraryTest.class;
        InputStream inputStream = clazz.getResourceAsStream("/fileTest.txt");
        String content = new String(Files.readAllBytes(Paths.get("src/main/resources/input.txt")));
        List<Integer> adapters = Arrays.stream(content.split("\n")).map(Integer::parseInt).collect(Collectors.toList());
        Integer result = new Aoc().solve(adapters);
        System.out.println(result);
    }

    @Test
    public void part2Example() {
        Aoc aoc = new Aoc();
        List<Integer> adapters = asList(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4);
        Long result = aoc.part2(adapters);
        System.out.println(result);
    }

    @Test
    public void part2Example2() {
        Aoc aoc = new Aoc();

        List<Integer> adapters = asList(28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23, 49, 45, 19, 38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3);
        Long result = aoc.part2(adapters);
        System.out.println(result);
    }

    @Test
    public void part2() throws IOException {
        Class clazz = LibraryTest.class;
        InputStream inputStream = clazz.getResourceAsStream("/fileTest.txt");
        String content = new String(Files.readAllBytes(Paths.get("src/main/resources/input.txt")));
        List<Integer> adapters = Arrays.stream(content.split("\n")).map(Integer::parseInt).collect(Collectors.toList());
        Long result = new Aoc().part2(adapters);
        System.out.println(result);
    }
}