import { useQuery } from "@tanstack/react-query";
import api from "@/lib/api";
import type { ApiResponse, Page } from "@/api/types";
import type { UserSummaryDto, UserFilters } from "./types";

const USER_QUERY_KEY = "users";

export const useUsers = (filters: UserFilters = {}) => {
  return useQuery({
    queryKey: [USER_QUERY_KEY, filters],
    queryFn: async (): Promise<Page<UserSummaryDto>> => {
      const params = new URLSearchParams();

      if (filters.page !== undefined)
        params.append("page", filters.page.toString());
      if (filters.size !== undefined)
        params.append("size", filters.size.toString());
      if (filters.sort) params.append("sort", filters.sort);
      if (filters.direction) params.append("direction", filters.direction);
      if (filters.keyword) params.append("keyword", filters.keyword);
      if (filters.status) params.append("status", filters.status);

      const response = await api.get<ApiResponse<Page<UserSummaryDto>>>(
        `/api/users?${params.toString()}`
      );

      if (!response.data.isSuccess || !response.data.data) {
        throw new Error(response.data.message || "Failed to fetch users");
      }

      return response.data.data;
    },
    staleTime: 5 * 60 * 1000, // 5 minutes
  });
};
