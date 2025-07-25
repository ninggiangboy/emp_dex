import { Button } from "@/components/ui/button";
import { toast } from "sonner";
import { useLoginWithGoogle } from "../hooks/use-login-with-google";
import { Loader2Icon } from "lucide-react";
import React from "react";

export function LoginForm() {
  const { handleLogin, isPending } = useLoginWithGoogle(
    (response) => {
      toast.success(`Login successful ${response.data.data}`);
    },
    () => {
      toast.error("Login failed");
    }
  );

  return (
    <div className="flex flex-col gap-8 p-6">
      <div className="flex flex-col items-center gap-3 text-center">
        <h1 className="text-2xl font-extrabold tracking-tight">Welcome Back</h1>
        <p className="text-muted-foreground text-base">
          Sign in with your Google account to continue
        </p>
      </div>
      <div className="flex flex-col gap-4">
        <GoogleSignInButton isPending={isPending} onClick={handleLogin} />
        <div className="flex items-center gap-2">
          <div className="flex-1 h-px bg-muted" />
          <span className="text-sm text-muted-foreground">or</span>
          <div className="flex-1 h-px bg-muted" />
        </div>
        <Button variant="secondary" disabled={isPending}>
          More sign-in options coming soon
        </Button>
      </div>
    </div>
  );
}

const GoogleSignInButton: React.FC<{
  isPending: boolean;
  onClick: () => void;
}> = ({ isPending, onClick }) => (
  <Button
    variant="outline"
    className="flex items-center justify-center gap-2"
    onClick={onClick}
    disabled={isPending}
  >
    {isPending ? (
      <>
        <Loader2Icon className="animate-spin" />
        <span>Signing in...</span>
      </>
    ) : (
      <>
        <img src="/icons/google.svg" alt="Google" className="w-4 h-4" />
        <span>Sign in with Google</span>
      </>
    )}
  </Button>
);
