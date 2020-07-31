package com.jhbb.notes.data.mapper

import com.jhbb.notes.data.dto.NoteDTO
import com.jhbb.notes.presentation.vo.NoteVO

class DataMapper {
    companion object {
        fun map(noteDTO: NoteDTO): NoteVO {
            return NoteVO(noteDTO.id, noteDTO.data.title, noteDTO.data.isCompleted)
        }

        fun map(noteVO: NoteVO): NoteDTO {
            return NoteDTO(noteVO.id, NoteDTO.NoteData(noteVO.description, noteVO.completed))
        }
    }
}