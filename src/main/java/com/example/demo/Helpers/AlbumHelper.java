package com.example.demo.Helpers;

import com.example.demo.Service.Album.AlbumService;
import com.example.demo.Service.AlbumsPhotos.AlbumsPhotosService;
import com.example.demo.domain.Album;
import com.example.demo.domain.Family.Family;
import com.example.demo.domain.Photo;
import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class AlbumHelper {
    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumsPhotosService albumsPhotosService;

    public HashMap<String, Object> getJson(Album album, String uri){
        return new HashMap<>() {{
            put("id", album.getId());
            put("title", album.getTitle());
            put("description", album.getDescription());
            put("totalPhotos", albumService.countAllByAlbum(album.getId()));
            put("uri", uri);
        }};
    }

    public void deleteAlbumsInFamily(int familyId){
        albumsPhotosService.deletePhotosAlbumsRelationByFamilyId(familyId);
        albumsPhotosService.deletePhotosInFamilyAlbums(familyId);
        albumService.deleteAlbumsInFamily(familyId);
    }

    public void pointFamilyAlbumsToTmpFamily(Family family, Family tmpFamily){
        for(var album : family.getAlbums()){
            album.setFamily(tmpFamily);
            albumService.saveAlbum(album);
        }
        family.getDefaultAlbum().setFamily(tmpFamily);
        albumService.saveAlbum(family.getDefaultAlbum());
    }
}
