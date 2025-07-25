export type ApiResponse<T> = {
  data: T | null;
  message: string | null;
  errorCode: string | null;
  isSuccess: boolean;
};

export type Pageable = {
  page: number;
  size: number;
  sort?: string;
  direction?: "ASC" | "DESC";
};

export type Page<T> = {
  content: T[];
  totalElements: number;
  totalPages: number;
  pageSize: number;
  pageNumber: number;
  hasPrevious: boolean;
  hasNext: boolean;
  numberOfElements: number;
};
