package com.att.university.request.lesson;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with", toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class LessonAddRequest extends LessonRequest {

}
