FEES:

erDiagram
    STUDENT {
        int student_id PK
        string student_number UK
        string first_name
        string last_name
        string email
        string status
        int class_id FK
        date enrollment_date
    }

    CLASS {
        int class_id PK
        string class_name
        string grade_level
        string academic_year
    }

    FEE_TYPE {
        int fee_type_id PK
        string fee_name
        string description
        decimal default_amount
        string frequency
        boolean is_mandatory
        date effective_date
    }

    FEE_STRUCTURE {
        int fee_structure_id PK
        int class_id FK
        int fee_type_id FK
        decimal amount
        string academic_year
        date due_date
        boolean is_active
    }

    STUDENT_FEE {
        int student_fee_id PK
        int student_id FK
        int fee_structure_id FK
        decimal amount_due
        decimal amount_paid
        decimal balance
        date due_date
        string status
        date created_date
        string academic_term
    }

    PAYMENT {
        int payment_id PK
        int student_id FK
        decimal total_amount
        date payment_date
        string payment_method
        string transaction_reference
        string payment_status
        string receipt_number
        text notes
    }

    PAYMENT_DETAIL {
        int payment_detail_id PK
        int payment_id FK
        int student_fee_id FK
        decimal amount_paid
        date payment_date
    }

    DISCOUNT {
        int discount_id PK
        string discount_name
        string discount_type
        decimal discount_value
        date valid_from
        date valid_to
        boolean is_active
    }

    STUDENT_DISCOUNT {
        int student_discount_id PK
        int student_id FK
        int discount_id FK
        date applied_date
        string academic_year
        boolean is_active
    }

    FINE {
        int fine_id PK
        int student_id FK
        string fine_type
        decimal fine_amount
        string reason
        date fine_date
        date due_date
        string status
    }

    %% Relationships
    STUDENT }o--|| CLASS : "enrolled_in"
    STUDENT ||--o{ STUDENT_FEE : "has"
    STUDENT ||--o{ PAYMENT : "makes"
    STUDENT ||--o{ STUDENT_DISCOUNT : "receives"
    STUDENT ||--o{ FINE : "incurs"

    CLASS ||--o{ FEE_STRUCTURE : "has"
    
    FEE_TYPE ||--o{ FEE_STRUCTURE : "defines"
    FEE_STRUCTURE ||--o{ STUDENT_FEE : "generates"

    PAYMENT ||--o{ PAYMENT_DETAIL : "contains"
    PAYMENT_DETAIL }o--|| STUDENT_FEE : "settles"

    DISCOUNT ||--o{ STUDENT_DISCOUNT : "applied_to"