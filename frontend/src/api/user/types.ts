export type UserSummaryDto = {
  id: string;
  username: string;
  email: string;
  status: string;
  lastLogin: string;
};

export type UserFilters = {
  page?: number;
  size?: number;
  sort?: string;
  direction?: "ASC" | "DESC";
  keyword?: string;
  status?: string;
};
