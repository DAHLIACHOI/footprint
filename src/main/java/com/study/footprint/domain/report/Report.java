package com.study.footprint.domain.report;

import com.study.footprint.common.converter.common.ReportReasonTypeCd;
import com.study.footprint.common.converter.common.ReportTargetTypeCd;
import com.study.footprint.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ReportTargetTypeCd reportTargetTypeCd;

    private ReportReasonTypeCd reportReasonTypeCd;

    private Long targetId;

    private Long reportedUserId;

    @Builder
    public Report(Long id, ReportTargetTypeCd reportTargetTypeCd, ReportReasonTypeCd reportReasonTypeCd, Long targetId, Long reportedUserId) {
        this.id = id;
        this.reportTargetTypeCd = reportTargetTypeCd;
        this.reportReasonTypeCd = reportReasonTypeCd;
        this.targetId = targetId;
        this.reportedUserId = reportedUserId;
    }
}
