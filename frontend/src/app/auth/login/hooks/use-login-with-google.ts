import api from "@/lib/api";
import { useGoogleLogin } from "@react-oauth/google";
import { useState, useCallback } from "react";

export const useLoginWithGoogle = (
  onSuccess: (response: any) => void,
  onError: (error: any) => void
) => {
  const [isPending, setIsPending] = useState(false);

  const handleGoogleSuccess = useCallback(
    async (codeResponse: any) => {
      try {
        const response = await api.post("/api/auth/oauth2/google", {
          code: codeResponse.code,
        });
        onSuccess(response);
      } catch (error) {
        onError(error);
      } finally {
        setIsPending(false);
      }
    },
    [onSuccess, onError]
  );

  const handleGoogleError = useCallback(
    (error: any) => {
      setIsPending(false);
      onError(error);
    },
    [onError]
  );

  const googleLogin = useGoogleLogin({
    flow: "auth-code",
    onSuccess: handleGoogleSuccess,
    onError: handleGoogleError,
  });

  const handleLogin = useCallback(() => {
    setIsPending(true);
    googleLogin();
  }, [googleLogin]);

  return {
    isPending,
    handleLogin,
  };
};
