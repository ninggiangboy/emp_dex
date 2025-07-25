import { UserTable } from "@/components/user-table";

export default function UserDashboardPage() {
  return (
    <div className="container mx-auto py-6">
      <div className="mb-6">
        <h1 className="text-3xl font-bold">User Management</h1>
        <p className="text-muted-foreground">
          Manage and view all users in the system
        </p>
      </div>
      <UserTable />
    </div>
  );
}
