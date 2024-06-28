package ait.album.test;

import ait.album.dao.Album;
import ait.album.dao.AlbumImpl;
import ait.album.model.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {
    Album album;
    Photo[] photos;

    @BeforeEach
    void setUp() {
        album = new AlbumImpl(10);
        photos = new Photo[9];
        photos[0] = new Photo(1, 111, "Photo1", "Url1", LocalDateTime.parse("2024-02-13T14:11:30"));
        photos[1] = new Photo(2, 222, "Photo2", "Url2", LocalDateTime.parse("2024-01-14T09:15:30"));
        photos[2] = new Photo(2, 333, "Photo3", "Url3", LocalDateTime.parse("2024-05-12T10:15:30"));
        photos[3] = new Photo(3, 444, "Photo4", "Url4", LocalDateTime.parse("2024-07-11T11:12:30"));
        photos[4] = new Photo(1, 555, "Photo5", "Url5", LocalDateTime.parse("2024-02-13T12:10:30"));
        photos[5] = new Photo(4, 666, "Photo6", "Url6", LocalDateTime.parse("2023-10-20T23:15:30"));
        photos[6] = new Photo(5, 777, "Photo7", "Url7", LocalDateTime.parse("2023-09-01T22:15:30"));
        photos[7] = new Photo(4, 888, "Photo8", "Url8", LocalDateTime.parse("2024-06-20T13:15:30"));
        photos[8] = new Photo(6, 999, "Photo9", "Url9", LocalDateTime.parse("2023-12-03T09:15:30"));
        for (int i = 0; i < photos.length; i++) {
            album.addPhoto(photos[i]);
        }
    }

    @Test
    void addPhoto() {
        boolean flag;
        try {
            album.addPhoto(null);
            flag = true;
        } catch (RuntimeException e) {
            flag = false;
            assertEquals("Photo is null", e.getMessage());
        }
        assertFalse(flag);
        assertFalse(album.addPhoto(photos[3]));
        Photo photo = new Photo(5, 001, "Photo10", "Url10", LocalDateTime.now());
        assertTrue(album.addPhoto(photo));
        assertEquals(10, album.size());
        photo = new Photo(5, 002, "Photo11", "Url10", LocalDateTime.now());
        assertFalse(album.addPhoto(photo));
    }

    @Test
    void removePhoto() {
        assertTrue(album.removePhoto(photos[3].getPhotoId(), photos[3].getAlbumId()));
        assertEquals(8, album.size());
        assertFalse(album.removePhoto(photos[3].getPhotoId(), photos[3].getAlbumId()));
        assertFalse(album.removePhoto(4545, 2));
        assertFalse(album.removePhoto(photos[3].getPhotoId(), 2));
        assertFalse(album.removePhoto(4545, photos[3].getAlbumId()));
    }

    @Test
    void updatePhoto() {
        assertEquals("Url4", photos[3].getUrl());
        assertTrue(album.updatePhoto(photos[3].getPhotoId(), photos[3].getAlbumId(), "Url10"));
        assertEquals("Url10", photos[3].getUrl());
        assertFalse(album.updatePhoto(4545, photos[3].getAlbumId(), "Url10"));
        assertFalse(album.updatePhoto(photos[3].getPhotoId(), 4545, "Url10"));
    }

    @Test
    void getPhotoFromAlbum() {
        assertEquals(photos[4], album.getPhotoFromAlbum(photos[4].getPhotoId(), photos[4].getAlbumId()));
        assertNull(album.getPhotoFromAlbum(4545, photos[5].getAlbumId()));
        assertNull(album.getPhotoFromAlbum(photos[4].getPhotoId(), 4545));
        assertNull(album.getPhotoFromAlbum(photos[4].getPhotoId(), photos[5].getAlbumId()));
    }

    @Test
    void getAllPhotoFromAlbum() {
        Photo[] actual = album.getAllPhotoFromAlbum(4);
        Photo[] expected = {photos[5], photos[7]};
        assertArrayEquals(expected, actual);
        Photo[] actual1 = album.getAllPhotoFromAlbum(4545);
        Photo[] expected1 = {};
        assertArrayEquals(expected1, actual1);
    }

    @Test
    void getPhotoBetweenDate() {
    }

    @Test
    void size() {
        assertEquals(9, album.size());
    }
}