package com.hq.dto;

import com.hq.model.Contents;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by huang on 26/4/2019.
 */
@Getter
@Setter
public class ArchiveDto
{
    private String date;
    private String count;
    private List<Contents> articles;
}
