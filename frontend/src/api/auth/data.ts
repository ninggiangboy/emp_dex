import api from "@/lib/api";
import type { ApiResponse } from "@/api/types";

export const authApi = {
  loginWithGoogle: async (code: string) => {
    const response = await api.post<ApiResponse<string>>(
      "/api/auth/oauth2/google",
      {
        code,
      }
    );
    return response.data;
  },
};
