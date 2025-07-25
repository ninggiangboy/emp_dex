import { create } from "zustand";

type AuthState = {
  token: string | null;
  isAuthenticated: boolean;
  loading: boolean;
  permissions: string[];
};

type AuthAction = {
  setToken: (token: string | null) => void;
  logout: () => void;
  setLoading: (loading: boolean) => void;
};

export const useAuth = create<AuthState & AuthAction>((set) => ({
  token: null,
  isAuthenticated: false,
  loading: false,
  permissions: [],

  setToken: (token) =>
    set({
      token,
      isAuthenticated: !!token,
      loading: false,
    }),
  logout: () =>
    set({
      token: null,
      isAuthenticated: false,
      loading: false,
      permissions: [],
    }),
  setLoading: (loading) => set({ loading }),
}));
