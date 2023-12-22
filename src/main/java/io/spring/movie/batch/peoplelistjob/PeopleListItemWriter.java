//package io.spring.movie.batch.peoplelistjob;
//
//import io.spring.movie.batch.temp.ActorTemp;
//import io.spring.movie.batch.temp.DirectorTemp;
//import io.spring.movie.batch.temp.PeopleTemp.People;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemWriter;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//public class PeopleListItemWriter implements ItemWriter<List<People>> {
//
//    private final ActorTempRepository actorTempRepository;
//    private final DirectorTempRepository directorTempRepository;
//
//    @Override
//    public void write(Chunk<? extends List<People>> chunks) {
//        if (chunks != null) {
//            for (List<People> chunk : chunks) {
//                for (People people : chunk) {
//                    if (people instanceof ActorTemp) {
//                        actorTempRepository.save((ActorTemp) people);
//                    }
//                    if (people instanceof DirectorTemp) {
//                        directorTempRepository.save((DirectorTemp) people);
//                    }
//                }
//            }
//        }
//    }
//}
