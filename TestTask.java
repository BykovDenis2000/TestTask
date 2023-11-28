package tasks;

import java.io.*;
import java.util.*;

public class TestTask {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите путь к входному файлу: ");
        String inputFilePath = scanner.nextLine();

        System.out.print("Введите путь к выходному файлу: ");
        String outputFilePath = scanner.nextLine();

        System.out.println("Выберите критерий сортировки:");
        System.out.println("  1 - Алфавитный порядок");
        System.out.println("  2 - По количеству символов в строке");
        System.out.println("  3 - По слову в строке (по порядковому номеру)");

        int sortingCriterion = scanner.nextInt();

        List<String> lines = readLinesFromFile(inputFilePath);

        switch (sortingCriterion) {
            case 1:
                Collections.sort(lines);
                break;
            case 2:
                lines.sort(Comparator.comparingInt(String::length));
                break;
            case 3:
                System.out.print("Введите порядковый номер слова: ");
                int wordPosition = scanner.nextInt();
                lines.sort(Comparator.comparing(s -> getWordAtPosition(s, wordPosition)));
                break;
            default:
                System.out.println("Некорректный критерий сортировки.");
                System.exit(1);
        }

        writeSortedLinesToFile(lines, outputFilePath);

        scanner.close();
        System.out.println("Программа завершена");
    }

    private static List<String> readLinesFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lines;
    }

    private static void writeSortedLinesToFile(List<String> lines, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Map<String, Integer> lineCountMap = new HashMap<>();

            for (String line : lines) {
                lineCountMap.put(line, lineCountMap.getOrDefault(line, 0) + 1);
            }

            for (String line : lines) {
                writer.write(line + " " + lineCountMap.get(line));
                writer.newLine();
            }
            System.out.println("Сортировка и запись завершены. Результаты записаны в файл: " + fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getWordAtPosition(String line, int position) {
        String[] words = line.split("\\s+");
        if (position > 0 && position <= words.length) {
            return words[position - 1];
        } else {
            throw new IllegalArgumentException("В строке "+"'"+ line +"'" + " нет слова на позиции " + position);
        }
    }
}
