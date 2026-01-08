package it.tsm.wiam.pokemon.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import it.tsm.wiam.pokemon.exception.PokemonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhotoGridFsService {

    //TODO: PER ORA non lo uso, lo lascio qui tanto per
    private final GridFsTemplate gridFsTemplate;

    // meotodo per salvare la foto
    public void savePhotoWithId(String id, MultipartFile file) {

        DBObject metadata = new BasicDBObject();
        metadata.put("_id", new ObjectId(id));

        try {
            gridFsTemplate.store(
                    file.getInputStream(),
                    file.getOriginalFilename(),
                    file.getContentType(),
                    metadata
            );
        }catch (Exception e){
            log.error("Error on savePhotoWithId with error: {}",e.getMessage());
            throw new PokemonException("PKM-500","Error on save photo","Errore durante il salvataggio della foto");
        }
    }

    // metodo per caricare la foto
    public byte[] loadPhotoById(String id) throws IOException {

        GridFSFile file = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(new ObjectId(id)))
        );

        if (file == null) {
            throw new FileNotFoundException("Photo not found");
        }

        try (InputStream is = gridFsTemplate
                .getResource(file)
                .getInputStream()) {

            return is.readAllBytes(); // ✅ Java native
        }
    }
}
