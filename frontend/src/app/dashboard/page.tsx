export default function DashboardPage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
        <p className="text-gray-600">Welcome to your EmpDex dashboard</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div className="bg-white p-6 rounded-lg border border-gray-200">
          <h3 className="text-lg font-semibold text-gray-900">Candidates</h3>
          <p className="text-3xl font-bold text-blue-600">12</p>
          <p className="text-sm text-gray-600">Active candidates</p>
        </div>

        <div className="bg-white p-6 rounded-lg border border-gray-200">
          <h3 className="text-lg font-semibold text-gray-900">Employees</h3>
          <p className="text-3xl font-bold text-green-600">8</p>
          <p className="text-sm text-gray-600">Total employees</p>
        </div>

        <div className="bg-white p-6 rounded-lg border border-gray-200">
          <h3 className="text-lg font-semibold text-gray-900">
            Job Descriptions
          </h3>
          <p className="text-3xl font-bold text-purple-600">5</p>
          <p className="text-sm text-gray-600">Active positions</p>
        </div>

        <div className="bg-white p-6 rounded-lg border border-gray-200">
          <h3 className="text-lg font-semibold text-gray-900">Recruitment</h3>
          <p className="text-3xl font-bold text-orange-600">3</p>
          <p className="text-sm text-gray-600">Ongoing processes</p>
        </div>
      </div>
    </div>
  );
}
