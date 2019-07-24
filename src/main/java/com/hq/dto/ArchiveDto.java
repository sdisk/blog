package com.hq.dto;

import com.hq.model.Contents;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huang on 26/4/2019.
 */
@Getter
@Setter
public class ArchiveDto implements Serializable
{
    private static final long serialVersionUID = 25861610632183070L;
    private String date;
    private String count;
    private List<Contents> articles;
}
