package io.spring.movie.batch.peoplelistjob;

import io.spring.movie.batch.temp.PeopleTemp;
import io.spring.movie.exception.CustomBatchException;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PeopleListFlatFileItemWriter extends FlatFileItemWriter<List<PeopleTemp>> {

    private final String filePath;

    public PeopleListFlatFileItemWriter(String filePath) {
        this.filePath = filePath;
        WritableResource resource = new FileSystemResource(filePath);
        LineAggregator<List<PeopleTemp>> lineAggregator = new PassThroughLineAggregator<>();

        this.setEncoding("UTF-8");
        this.setResource(resource);
        this.setLineAggregator(lineAggregator);
    }

    @Override
    public void write(Chunk<? extends List<PeopleTemp>> chunks) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            if (chunks != null) {
                for (List<PeopleTemp> chunk : chunks) {
                    for (PeopleTemp peopleTemp : chunk) {
                        String line = String.format("%s,%s", peopleTemp.getPeopleCode(), peopleTemp.getPeopleName());
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBatchException("CSV 파일 생성 중 에러가 발생했습니다.");
        }
    }

}

