package org.base.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.base.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "api_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apiLogId;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "method_name")
    private String methodName;

    @Lob
    @Column(columnDefinition = "TEXT", name = "parameters")
    private String parameters;

    @Column(name = "username")
    private String username;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "repository_accessed")
    private List<String> repositoryAccessed;

    @Lob
    @Column(columnDefinition = "TEXT", name = "request_body")
    private String requestBody;

    @Lob
    @Column(columnDefinition = "TEXT", name = "response_body")
    private String responseBody;

    @Column(name = "status_code")
    private int statusCode;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "user_ip")
    private String userIp;

    @Column(name = "referer")
    private String referer;

    @Column(name = "correlation_id")
    private String correlationId;

    @Column(name = "execution_time")
    private Long executionTime;

    @Column(name = "server_node")
    private String serverNode;


    public ApiLog(String endpoint, String parameters, String username, LocalDateTime timestamp, long executionTime) {
        this.endpoint = endpoint;
        this.parameters = parameters;
        this.username = username;
        this.timestamp = timestamp;
        this.executionTime = executionTime;
    }

}
