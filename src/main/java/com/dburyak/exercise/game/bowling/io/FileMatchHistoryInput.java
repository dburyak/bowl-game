package com.dburyak.exercise.game.bowling.io;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UncheckedIOException;

@AllArgsConstructor
public class FileMatchHistoryInput implements MatchHistoryInput {
    private File inputFile;

    @Override
    public InputStream asInputStream() {
        try {
            return new FileInputStream(inputFile);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException("input file not found", e);
        }
    }
}
