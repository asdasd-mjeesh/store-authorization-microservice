package com.access_security.entity.producer;

import com.access_security.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Contact extends BaseEntity {

    private String phone;
}
