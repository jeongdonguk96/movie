package io.spring.movie.batch.peoplelistjob;

import io.spring.movie.entity.ActorTemp;
import io.spring.movie.entity.DirectorTemp;
import io.spring.movie.entity.People;
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

public class PeopleListFlatFileItemWriter extends FlatFileItemWriter<List<People>> {

    private final String actorFilePath;
    private final String directorFilePath;

    public PeopleListFlatFileItemWriter(String actorFilePath, String directorFilePath) {
        this.actorFilePath = actorFilePath;
        this.directorFilePath = directorFilePath;
        WritableResource actorResource = new FileSystemResource(actorFilePath);
        WritableResource directorResource = new FileSystemResource(directorFilePath);
        LineAggregator<List<People>> lineAggregator = new PassThroughLineAggregator<>();

        this.setEncoding("UTF-8");
        this.setResource(actorResource);
        this.setResource(directorResource);
        this.setLineAggregator(lineAggregator);
    }

    @Override
    public void write(Chunk<? extends List<People>> chunks) {

        try (BufferedWriter actorWriter = new BufferedWriter(new FileWriter(actorFilePath, true));
             BufferedWriter directorWriter = new BufferedWriter(new FileWriter(directorFilePath, true))) {

            if (chunks != null) {
                for (List<People> chunk : chunks) {
                    for (People people : chunk) {
                        if (people instanceof ActorTemp actorTemp) {
                            String line = String.format("%s,%s", actorTemp.getPeopleCode(), actorTemp.getPeopleName());
                            actorWriter.write(line);
                            actorWriter.newLine();
                        } else if (people instanceof DirectorTemp directorTemp) {
                            String line = String.format("%s,%s", directorTemp.getPeopleCode(), directorTemp.getPeopleName());
                            directorWriter.write(line);
                            directorWriter.newLine();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBatchException("CSV 파일 생성 중 에러가 발생했습니다.");
        }
    }

}

